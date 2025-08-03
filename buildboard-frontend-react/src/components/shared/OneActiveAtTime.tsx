import { Children, useState, type ReactNode } from "react";

type MaybeArray<T> = T | T[]
type OneActiveAtTimeProps = {
	children: MaybeArray<ReactNode>,
	activeCls : string,
	nonActiveCls: string, 
	initActive? : number
};

const OneActiveAtTime = ({ children, initActive = 0, activeCls, nonActiveCls }: OneActiveAtTimeProps) => {
	const [iActive, setIActive] = useState<number>(initActive);

	return (
		<>
			{Children.map(children, (child, i) => (
				<div
					onMouseEnter={(_) => setIActive(i)}
					className={`${iActive == i ? activeCls : nonActiveCls}`}
				>
					{child}
				</div>
			))}
		</>
	);
};

export default OneActiveAtTime;
