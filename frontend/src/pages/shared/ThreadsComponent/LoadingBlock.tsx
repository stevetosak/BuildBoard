type LoadingBlockProp = {
	w: string | number;
	h: string | number;
};

const LoadingBlock = ({ w, h }: LoadingBlockProp) => {
	return <div style={{width: w, height: h}} className="bg-[#011837] animate-pulse rounded"></div> 
};

export default LoadingBlock;
