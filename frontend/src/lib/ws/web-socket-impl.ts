import {useEffect, useReducer, useRef, useCallback} from 'react';
import {Client, type IMessage} from '@stomp/stompjs';
import SockJS from "sockjs-client/dist/sockjs"

type SubscriptionCallback = (message: any) => void;

type State = {
    client: Client | null;
    subscriptions: Map<string, any>;
};

type Action =
    | {type: 'SET_CLIENT'; payload: Client}
    | {type: 'ADD_SUBSCRIPTION'; payload: {destination: string; subscription: any}}
    | {type: 'REMOVE_SUBSCRIPTION'; payload: string}
    | {type: 'CLEAR_CLIENT'};

const reducer = (state: State, action: Action): State => {
    switch (action.type) {
        case 'SET_CLIENT':
            return {...state, client: action.payload};
        case 'ADD_SUBSCRIPTION':
            return {...state, subscriptions: new Map(state.subscriptions).set(action.payload.destination, action.payload.subscription)};
        case 'REMOVE_SUBSCRIPTION':
            {
                const updatedSubscriptions = new Map(state.subscriptions);
                updatedSubscriptions.delete(action.payload);
                return {...state, subscriptions: updatedSubscriptions};
            }
        case 'CLEAR_CLIENT':
            return {client: null, subscriptions: new Map()};
        default:
            return state;
    }
};

export const useWebSocketService = (
    webSocketUrl: string,
    onConnectCallback: () => void,
    onErrorCallback: (error: string) => void,
) => {
    const [state, dispatch] = useReducer(reducer, {
        client: null,
        subscriptions: new Map(),
    });

    const clientRef = useRef<Client | null>(null);
    const isConnected = useRef(false);

    useEffect(() => {
        clientRef.current = state.client;
    }, [state.client]);

    const connect = useCallback(() => {
        if (state.client || isConnected.current) return;

        const client = new Client({
            webSocketFactory: () => new SockJS(webSocketUrl),
            debug: str => console.log('debugLog', str),
            reconnectDelay: 5000,
            heartbeatIncoming: 1000,
            heartbeatOutgoing: 1000,
            onConnect: () => {
                isConnected.current = true;
                console.log('WebSocket connected');
                onConnectCallback();
            },
            onStompError: error => {
                onErrorCallback(error.headers['message'] || 'Unknown error');
            },
        });

        client.activate();
        dispatch({type: 'SET_CLIENT', payload: client});
    }, [state.client, webSocketUrl, onConnectCallback, onErrorCallback]);

    const subscribe = useCallback(
        (destination: string, callback: SubscriptionCallback) => {
            const client = clientRef.current;
            if (!client || !isConnected.current) return;

            if (state.subscriptions.has(destination)) return;

            const subscription = client.subscribe(destination, (message: IMessage) => {
                console.log("recieved message subs callback")
                if (message.body) callback(JSON.parse(message.body));
            });

            dispatch({type: 'ADD_SUBSCRIPTION', payload: {destination, subscription}});
        },
        [state.subscriptions],
    );

    const send = useCallback((destination: string, body: Record<string, any> = {}) => {
        const client = clientRef.current;
        if (!client || !isConnected.current) return;
        client.publish({destination, body: JSON.stringify(body)});
    }, []);

    const unsubscribe = useCallback((destination: string) => {
        const subscription = state.subscriptions.get(destination);
        if (subscription) {
            subscription.unsubscribe();
            dispatch({type: 'REMOVE_SUBSCRIPTION', payload: destination});
        }
    }, [state.subscriptions]);

    const disconnect = useCallback(() => {
        const client = clientRef.current;
        if (client && isConnected.current) {
            state.subscriptions.forEach(subscription => subscription.unsubscribe());
            client.deactivate();
            dispatch({type: 'CLEAR_CLIENT'});
            isConnected.current = false;
        }
    }, [state.subscriptions]);

    return {connect, subscribe, send, unsubscribe, disconnect};
};