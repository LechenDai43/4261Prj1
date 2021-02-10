<? php

require "connection.php";

$email = $POST["email"];

$sql = "SELECT email, password FROM users where email = '$email'";

$result = mysqli_query($con, $sql);
$response = array();

if (mysql_num_rows($result)>0) {
    $row = mysqli_fetch_assoc($result);
    mail($row["email"], "Your Account Passsword is" $row["password"], "weMessage", "sherryq1125@gmail.com");
    echo "SUCCESS";
} else {
     echo "FAIL";
}


?>