import {Card, CardContent, CardFooter, CardHeader} from "@/components/ui/card.tsx";
import {Check, Reply, X} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";



export const ChildThread = ({className}:{className:string}) => {
    return (
        <Card className={`border rounded-3xl ${className} gap-1`}>
            <CardHeader className="flex justify-between items-center px-4 py-2">
                <div className="flex gap-1 items-center">
                    <div>usr img</div>
                    <div>username</div>
                </div>
                <div className="text-sm text-gray-500">25.08.2025</div>
            </CardHeader>

            <hr className="my-1" />

            <CardContent className="px-4 py-1 text-sm text-gray-800">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit,
            </CardContent>

            <CardFooter className="flex justify-between items-center px-4 py-2">
                <div className="flex gap-3 text-sm text-gray-700">
                    <div className="flex items-center gap-1">
                        <Check className="w-4 h-4" />
                        <span>23</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <X className="w-4 h-4" />
                        <span>13</span>
                    </div>
                </div>
                <div>
                    <Button
                        className="p-1 hover:-translate-y-0.5 hover:bg-gray-200 transition"
                        onClick={() => console.log("click")}
                    >
                        <Reply className="w-4 h-4" />
                    </Button>
                </div>
            </CardFooter>
        </Card>

    )
}