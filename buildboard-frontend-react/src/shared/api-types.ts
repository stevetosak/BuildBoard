export type Channel = {
    name: string;
};

export type Projects = {
    name: string;
};

export type Topic = {
    name: string;
};

export type Friend = {
    username : string,
    logo : URL
}

export type NonNullUser = {
    username: string;
    interested: {
        projects: Projects[];
        topics: Topic[];
    };
   friends : Friend[]
};

export type UserProfile = NonNullUser | null;

export type ErrorMsg = {
    message : string,
    path : URL
}

export type Page<T> = {
    content : T,
    pageable : { 
        pageNumber:number
    }
};

export type NamedThread = {
    meta: {
        username: string;
        userLogo: URL;
        createdAt: string;
    };
    content: {
        title: string;
        tags: string[]; //Trebat da se razmislit za ova kako ke se handelvit
        content: string;
    };
};
