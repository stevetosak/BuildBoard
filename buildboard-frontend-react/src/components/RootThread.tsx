import {Card, CardContent, CardFooter, CardHeader} from "@/components/ui/card.tsx";
import {Check, Reply, X} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";

export const RootThread = () => {
    return (
        <Card className={"border rounded-3xl"}>
            <CardHeader className={"flex justify-between"}>
                <div className={"flex gap-2"}>
                    <div>
                        usr img
                    </div>
                    <div>
                        username
                    </div>
                </div>
                <div>
                    25.08.2025
                </div>
            </CardHeader>
            <hr/>
            <CardContent>
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                Ut enim ad minim veniam, quis nostrud exercitation
            </CardContent>
            <CardFooter className={"flex justify-between"}>
                <div className={"flex gap-5"}>
                    <div className={"flex justify-between "}>
                        <Check/>
                        <span>23</span>

                    </div>
                    <div className={"flex justify-between"}>
                        <X/>
                        <span>13</span>
                    </div>
                </div>
                <div>
                    <Button className={"hover:-translate-y-0.5 hover:bg-gray-300"} onClick={() => console.log("click")}><Reply/></Button>
                </div>
            </CardFooter>
        </Card>
    )
}