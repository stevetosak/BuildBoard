import PlaceHolderWithValue from "@pages/shared/placeholders/placeholder-value";
import Sidebar from "./wrapper";
import DataContextProvider from "../placeholders/data-context-provider";

const RightSidebar = {
	Wrapper: Sidebar,
	Body: PlaceHolderWithValue,
	Header: PlaceHolderWithValue,
	ContextOverrider: DataContextProvider
};

export default RightSidebar;
