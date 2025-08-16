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
	username: string;
	logo: URL;
};

export type UserProfile = {
	username: string;
	interested: {
		projects: Projects[];
		topics: Topic[];
	};
	friends: Friend[];
};


export type ErrorMsg = {
	message: string;
	path: URL;
};

export type Page<T> = {
	content: T;
	pageable: {
		pageNumber: number;
	};
};

export type NamedThread = {
	createdAt: string;
	creator: {
		username: string;
		logo: string;
	};
	content: {
		title: string;
		content: string;
		tags: string[];
	};
};

