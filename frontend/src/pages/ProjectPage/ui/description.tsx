import iconUrl from "@assets/Icon.jpg";
import { Button } from "@components/ui/button";
import { Link } from "react-router-dom";
import Markdown from "react-markdown";

const markdown = `
# Sample Markdown Document  

This is a simple example of **Markdown** with text, images, lists, and links.  

---

## 📌 Introduction  

Markdown is a lightweight markup language that you can use to format plain text.  
It’s widely used for documentation, README files, and even blog posts.  

---

## ✅ Features  

- Easy to write  
- Easy to read  
- Supports **bold**, *italic*, and inline code  
- Can display links, lists, tables, and images  

---

## 🔗 Links  

Here’s a link to [Markdown Guide](https://www.markdownguide.org) if you want to learn more.  

---

## 🖼 Images  

Here’s an example of an image:  

![Markdown Logo](https://upload.wikimedia.org/wikipedia/commons/4/48/Markdown-mark.svg)  

And another one with some description:  

![GitHub Octocat](https://github.githubassets.com/images/modules/logos_page/Octocat.png)  

`;

//TODO: vidi dali mozish aspect ratio da zadrzish na slikata
//TODO: add description and logo into projects 
const Description = () => {
	// const project = useOutletContext<Project>();

	return (
		<section
			className="grid px-[10%]"
			style={{
				gridTemplateRows: "minmax(15rem,20vh) min-content auto"
			}}
		>
			<div className="relative w-1/4 justify-self-center">
				<div className="absolute w-full h-full">
					<img
						className="w-full h-full"
						src={iconUrl}
					/>
				</div>
			</div>
			<div className="justify-self-end ">
				<Button variant={"outline"}>
					<Link to="/login">Send request</Link>
				</Button>
			</div>
			<div className="h-full overflow-scroll relative">
				<div className="w-full mt-5 text-lg absolute text-left justify-self-center">
					<div className="p-[2em] pt-[1em] bg-sidebar-bg rounded-xl w-full h-full  project-desc">
						<Markdown>{markdown}</Markdown>
					</div>
				</div>
			</div>
		</section>
	);
};

export default Description;
