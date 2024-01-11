<?php

class CoachController
{

    public static function index(): string
    {
        $carriages = DB::get("*", "coach", "", []);
        if (is_null($carriages)) {
            Response::codeNotFound();
            Response::abort();
        }

        return Response::JSON($carriages);
    }

    public static function get(): string
    {
        if (!Request::params()->nonempty("id")) {
            Response::codeBadRequest();
            Response::abort();
        }

        $carriage = DB::get("*", "coach", "id = ?", [Request::params()->get("id")]);
        if (is_null($carriage) || empty($carriage)) {
            Response::codeNotFound();
            Response::abort();
        }

        return Response::JSON($carriage[0]);
    }
}
