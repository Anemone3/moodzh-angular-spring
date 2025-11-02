
type User = {
    id: string;
    email: string;
    username: string
    profile: string | null;
    providerId: string | null;
    provider: string;
    linkSocial: Partial<SocialLinks>;
    location: Locations
    createdAt: string;
    lastUpdated: string;
}

type SocialLinks = {
    instagram: string;
    facebook: string;
    twitter: string;
    youtube:string;
    linkedin: string;
    website: string;
}


type Locations = {
    country: string;
    city: string;
}