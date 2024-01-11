<?php

Route::get("/train/", TrainController::class, "index");
Route::get("/train/{id}/", TrainController::class, "get");
Route::get("/nextTrain/{station}/", TrainController::class, "next");

Route::get("/coach/", CoachController::class, "index");
Route::get("/coach/{id}/", CoachController::class, "get");

Route::get("/station/", StationController::class, "index");
Route::get("/station/{name}/", StationController::class, "get");
