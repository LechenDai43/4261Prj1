<?php
$dbname = "project1";
$dbhost = "cs4261.cteat7fyzof8.us-east-1.rds.amazonaws.com";
$dbport = "3306";
$dbuser = "karrywang";
$dbpass = "wangjunkai";

$con = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname, $dbport);

if ($con) {
    echo "fine";
    $result = mysqli_query($con, "select * from users;");
    echo mysqli_num_rows($result);
}
else {
    echo "not fine";
}
?>