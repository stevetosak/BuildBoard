import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { router } from "./router";
import { RouterProvider } from "react-router-dom";
import { QueryClientProvider, QueryClient} from "@tanstack/react-query";

const queryClient = new QueryClient()

createRoot(document.getElementById("root")!).render(
	<StrictMode>
		<QueryClientProvider client={queryClient}>
				<RouterProvider router={router}/>
		</QueryClientProvider>
	</StrictMode>,
);
