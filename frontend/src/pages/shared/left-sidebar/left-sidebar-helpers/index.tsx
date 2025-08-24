import PlaceHolderWithValue from "../../placeholders/placeholder-value";
import Wrapper from "./wrapper";
import PlaceHolderNoValue from "../../placeholders/placeholder";
import DataContextProvider from "../../placeholders/data-context-provider";

const LeftSidebarBuilder = { 
    Wrapper:Wrapper,
    HeaderInsideData: PlaceHolderNoValue,
    BodyInsideData:PlaceHolderNoValue,
    BodyOutsideData:PlaceHolderWithValue,   
    HeaderOutsideData:PlaceHolderWithValue,
    ContextOverrider:DataContextProvider
}

export default LeftSidebarBuilder
