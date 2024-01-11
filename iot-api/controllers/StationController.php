<?php

class StationController
{

    public static function index(): string
    {
        $platforms = DB::get("*", "station", "", []);
        if (is_null($platforms)) {
            Response::codeNotFound();
            Response::abort();
        }

        return Response::JSON($platforms);
    }

    public static function get(): string
    {
        if (!Request::params()->nonempty("name")) {
            Response::codeBadRequest();
            Response::abort();
        }

        $platform = DB::get("*", "station", "name = ?", [Request::params()->get("name")]);
        if (is_null($platform) || empty($platform)) {
            Response::codeNotFound();
            Response::abort();
        }

        return Response::JSON($platform[0]);
    }
}
