<?php


class Path {

	private function __construct() {}

	public static function system() : string {
		return SYSTEM_DIR . '/';
	}

}