import {
	createBrowserRouter,
	createRoutesFromElements,
	Route,
} from "react-router-dom";
import HomePage  from "@/pages/HomePage";
import {loader as homePageLoader} from "@pages/HomePage/data/fetchUser"

export const router = createBrowserRouter(
	createRoutesFromElements(
		<>
			<Route path={"/"}>
				<Route
					index
					loader={homePageLoader}
					element={<HomePage />}
				/>
				<Route path={"topics/:id"}></Route>
				<Route path={"projects/:id/"}>
					<Route path={"channels/:channelName"}></Route>
					<Route path={"requests"}></Route>
				</Route>
				<Route path={":username/profile"}></Route>
				<Route path={"/about"}></Route>
			</Route>
		</>,
	),
);
