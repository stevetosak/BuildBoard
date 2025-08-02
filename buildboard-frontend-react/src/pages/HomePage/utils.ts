import type { LoaderFunctionArgs } from "react-router-dom"

type Channel = {
    name : string,
    url : URL  
}

type Projects ={ 
    name : string,
    url : URL  
}

type Tag = {
    name : string
}

export type NonNullUser = {
    username: string,
    channels : Channel[],
    projects: Projects[],
    tags : Tag[],
    friends: { 
        username : NonNullUser['username'],
        url : URL 
    }[] 
};
export type User = NonNullUser | null;

const mockUser: User = {
  username: "viktor_dev",
  channels: [
    { name: "Frontend Weekly", url: new URL("https://youtube.com/frontendweekly") },
    { name: "Code & Coffee", url: new URL("https://twitch.tv/codeandcoffee") },
    { name: "JS Nuggets", url: new URL("https://youtube.com/jsnuggets") },
    { name: "DevLog Podcast", url: new URL("https://spotify.com/devlog") }
  ],
  projects: [
    { name: "BuildBoard", url: new URL("https://github.com/viktor/buildboard") },
    { name: "ThemeSwitch", url: new URL("https://github.com/viktor/themeswitch") },
    { name: "SocketIO Chat", url: new URL("https://github.com/viktor/socketchat") },
    { name: "Portfolio 2025", url: new URL("https://viktor.dev/portfolio2025") },
    { name: "DevTimer", url: new URL("https://github.com/viktor/devtimer") },
    { name: "CodeSnip", url: new URL("https://github.com/viktor/codesnip") }
  ],
  tags: [
    { name: "TypeScript" },
    { name: "React" },
    { name: "WebSockets" },
    { name: "Open Source" },
    { name: "Animations" },
    { name: "Dark Mode" },
    { name: "Productivity" }
  ],
  friends: [
    { username: "elena_codes", url: new URL("https://devnetwork.com/users/elena_codes") },
    { username: "mario_dev", url: new URL("https://devnetwork.com/users/mario_dev") },
    { username: "techwitch", url: new URL("https://devnetwork.com/users/techwitch") },
    { username: "alex_node", url: new URL("https://devnetwork.com/users/alex_node") },
    { username: "katie_ui", url: new URL("https://devnetwork.com/users/katie_ui") },
    { username: "niko_stream", url: new URL("https://devnetwork.com/users/niko_stream") }
  ]
};

const mockAnonnymusUser: User = null 

export const loader = async ({request}:LoaderFunctionArgs) => {
    const queryParams = new URL(request.url).searchParams 
    return queryParams.get('user') === 'anonymus' ?  mockAnonnymusUser : mockUser
}