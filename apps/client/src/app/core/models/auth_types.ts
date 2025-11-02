

type AccessToken = {
    access_token: string;
    token_type: string;
    expires_in: string;
}




type LoginRequest = {
    email: string;
    password: string;
}

type RegiterRequest = {
    username: string;
    email: string
    password: string;
    verify_password: string;
    profile_url: string;
}