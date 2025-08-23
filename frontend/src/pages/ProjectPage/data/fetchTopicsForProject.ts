import type { SearchOptions } from "@pages/shared/ThreadsComponent"
import type { NamedThread, Page } from "@shared/api-utils"

const fetchTopicsForProject = (pageNumber:number, searchOptions:SearchOptions, projectName:string): Promise<Page<NamedThread[]>>  => { 

}

export default fetchTopicsForProject