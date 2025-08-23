import ThreadsComponent from "@pages/shared/ThreadsComponent";
import iconUrl from "@assets/Icon.jpg";

const Topics = () => {
	return (
		<section className="grid grid-rows-[minmax(15rem,20vh)_auto]">
            <div className="relative w-1/4 justify-self-center">
				<div className="absolute w-full h-full">
					<img
						className="w-full h-full"
						src={iconUrl}
					/>
				</div>
			</div>
			<ThreadsComponent />
		</section>
	);
};

export default Topics;
