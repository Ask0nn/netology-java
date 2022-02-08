DO '
DECLARE
    role_id INTEGER;
    user_id UUID;
BEGIN
    UPDATE public."user" SET username = ''Ask0n'', password = ''$2a$12$x2PJibaL95aca/YClM8Vn.oVAGkmZGIbnC.Ddb8k5bhhOeWYNagde''
    WHERE username = ''Ask0n'';
    IF NOT FOUND THEN
        INSERT INTO public."user" (username, password) VALUES (''Ask0n'', ''$2a$12$x2PJibaL95aca/YClM8Vn.oVAGkmZGIbnC.Ddb8k5bhhOeWYNagde'');
        SELECT id INTO role_id FROM public.role WHERE role.role = ''ROLE_USER'';
        SELECT id INTO user_id FROM public."user" WHERE username = ''Ask0n'';
        INSERT INTO public.user_role ("user_id", role_id) VALUES (user_id, role_id);
    END IF;
END ';