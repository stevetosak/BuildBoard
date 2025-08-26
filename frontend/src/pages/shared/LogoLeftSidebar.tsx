import iconUrl from "@assets/Icon.jpg";

const LogoLeftSidebar = () => {
	return (
		<div className="w-1/2 h-full">
			<img
				src={iconUrl}
				alt="Buildboard logo"
				className="w-full h-full"
			/>
		</div>
	);
};

export default LogoLeftSidebar;
