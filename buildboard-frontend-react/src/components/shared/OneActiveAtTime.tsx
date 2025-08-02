import { Children, useState, type ReactNode } from "react";

type MaybeArray<T> = T | T[]
type OneActiveAtTimeProps = {
	children: MaybeArray<ReactNode>;
	initActive? : number 
};

const OneActiveAtTime = ({ children, initActive = 0 }: OneActiveAtTimeProps) => {
	const [iActive, setIActive] = useState<number>(initActive);

	return (
		<>
			{Children.map(children, (child, i) => (
				<div
					onMouseEnter={(_) => setIActive(i)}
					className={`${iActive == i ? "text-accent" : "text-white"}`}
				>
					{child}
				</div>
			))}
		</>
	);
};

export default OneActiveAtTime;
