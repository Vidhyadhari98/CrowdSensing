<?php

class DB
{
    private static string $dbUser = 'root';
    private static string $dbPass = 'Password@123';

    private static bool $open = false;
    private static PDO $connection;

    private function __construct()
    {
    }

    public static function open(): void
    {
        if (self::$open) {
            return;
        }

        try {
            $dbConn = 'iot-project-20240110:europe-west1:iot-db-instance';
            $dbName = 'iot-db';
            $dsn = "mysql:unix_socket=/cloudsql/{$dbConn};dbname={$dbName}";
            self::$connection = new PDO(
                $dsn,
                self::$dbUser,
                self::$dbPass,
                [
                    PDO::ATTR_TIMEOUT => 5,
                    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                ]
            );

            self::$open = true;
        } catch (PDOException $e) {
            throw new RuntimeException(
                sprintf(
                    'Could not connect to the Cloud SQL Database. Check that ' .
                    'your username and password are correct, that the Cloud SQL ' .
                    'proxy is running, and that the database exists and is ready ' .
                    'for use. For more assistance, refer to %s. The PDO error was %s',
                    'https://cloud.google.com/sql/docs/mysql/connect-external-app',
                    $e->getMessage()
                ),
                (int)$e->getCode(),
                $e
            );
        }
    }

    public static function close(): void
    {
        if (!self::$open) {
            return;
        }
        self::$connection = null;
        self::$open = false;
    }

    public static function transaction(): void
    {
        self::$connection->beginTransaction();
    }

    public static function commit(): void
    {
        self::$connection->commit();
    }

    public static function prepare($string): object
    {
        self::open();

        return self::$connection->prepare($string);
    }

    public static function exists(string $tables, string $conditions, array $params): bool|null
    {
        $query = QueryBuilder::new()->exists()->select("*")->from($tables)->where($conditions)->build();
        $stmt = self::prepare($query);
        if (!$stmt) {
            return null;
        }
        $stmt->execute($params);

        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $stmt = null;

        return $result[0]['EXISTS'] == 1;
    }

    public static function get(string $targets, string $tables, string $conditions, array $params): array|null
    {
        $query = QueryBuilder::new()->select($targets)->from($tables)->where($conditions)->build();
        return self::execute($query, $params);
    }

    public static function execute(string $query, array $params): array|null
    {
        $stmt = self::prepare($query);
        if (!$stmt) {
            return null;
        }
        $stmt->execute($params);

        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $stmt = null;

        return $result;
    }

    // Remaining functions remain similar with minor modifications
    // ...

    public static function hasSqlStatementResponse($result): bool
    {
        return count($result) > 0;
    }
}
