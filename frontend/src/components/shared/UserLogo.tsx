import { useState } from "react";
import { Glasses } from "lucide-react";

type UserLogoProps = {
	url: URL;
    alt:string
};

const UserLogo = ({ url,alt }: UserLogoProps) => {
	const [isImgUrlOk, setIsImgUrlOk] = useState<boolean>(true);

	return isImgUrlOk ? (
		<img
            alt={alt}
			className="w-full h-full"
			src={url.toString()}
            onError={_ => setIsImgUrlOk(false)}
		/>
	) : (
		<Glasses />
	);
};

export default UserLogo;
