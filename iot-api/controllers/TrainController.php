<?php
class TrainController
{

    public static function index(): string
    {
        $trains = DB::get("*", "train", "", []);
        if (is_null($trains)) {
            Response::codeNotFound();
            Response::abort();
        }

        return Response::JSON($trains);
    }

    public static function get(): string
    {
        if (!Request::params()->nonempty("id")) {
            Response::codeBadRequest();
            Response::abort();
        }

        $train = DB::get("*", "train", "id = ?", [Request::params()->get("id")]);
        if (is_null($train) || empty($train)) {
            Response::codeNotFound();
            Response::abort();
        }

        return Response::JSON($train[0]);
    }

    public static function next(): string
    {
        if (!Request::params()->nonempty("station")) {
            Response::codeBadRequest();
            Response::abort();
        }
        $simulatedTrainId = 1;

        $train = DB::get("*", "train", "id = ?", [$simulatedTrainId]);
        if (is_null($train) || empty($train)) {
            Response::codeNotFound();
            Response::abort();
        }

        $carriages = DB::execute("SELECT * FROM coach WHERE train_id = ? ORDER BY position ASC", [$simulatedTrainId]);

        return Response::JSON(array('train' => $train[0], 'coaches' => $carriages));
    }
}
