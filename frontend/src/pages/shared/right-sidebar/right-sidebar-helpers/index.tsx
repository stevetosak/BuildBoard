import PlaceHolderWithValue from "@pages/shared/placeholders/placeholder-value";
import Sidebar from "./wrapper";
import DataContextProvider from "../../placeholders/data-context-provider";
import PlaceHolderNoValue from "@pages/shared/placeholders/placeholder";

const RightSidebarBuilder = {
	Wrapper: Sidebar,
	BodyOutsideData: PlaceHolderWithValue,
	BodyInsideData: PlaceHolderNoValue, 
	Header: PlaceHolderWithValue,
	ContextOverrider: DataContextProvider
};

export default RightSidebarBuilder;
