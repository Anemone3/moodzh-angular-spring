

type AccessToken = {
    access_token: string;
    token_type: string;
    expires_in: string;
}




type LoginRequest = {
    email: string;
    password: string;
}

type RegisterRequest = {
    username: string;
    email: string
    password: string;
    profile_url: string | null;
}