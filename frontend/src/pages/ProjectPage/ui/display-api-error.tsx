import type { ApiError } from "@shared/api-utils.ts";

type DisplayIfNotErrorProps = {
	error: ApiError ;
};

const DisplayApiError = ({ error }: DisplayIfNotErrorProps) => {
	return (
		<div>
			<p>STATUS: {error.status}</p>
			<p>Title</p>
			<p>{error.title}</p>
			<p>Description:</p>
			<p>{error.detail}</p>
		</div>
	);
};

export default DisplayApiError;
