<?php
require "connection.php";
if (isset($_POST["u1"])) {
    $key = $_POST["email"];
    $query = "select password, username from users where email like '%".$key."%';";
    $result = mysqli_query($con, $query); 
    if (mysqli_num_rows($result) != 1) {
        echo "Account not find.";
    }
    else {
        $row = mysqli_fetch_row($result);
        echo $row[0]."\r\n";
        echo $row[1];
    }
}
else if (isset($_POST["u2"])) {
    $email = $_POST["email"];
    $query = "select * from users where email like '".$email."';";
    $result = mysqli_query($con, $query);
    if (mysqli_num_rows($result) != 1) {
        echo "Email not used.";
    }
    else {
        echo "Email is used.";
    }
}
else if (isset($_POST["u3"])) {
    $email = $_POST["email"];
    $username = $_POST["name"];
    $password = $_POST["pass"];
    $query = "insert into users (email, username, password) values('".$email."','".$username."','".$password."');";
    $result = mysqli_query($con, $query);
    if ($result) {
        $upper_query = "select id from users where email like '".$email."';";
        $result = mysqli_query($con, $upper_query);
        $new_id = mysqli_fetch_row($result)[0];
        $query = "create table `".$email."` (id int primary key auto_increment not null, friend_id int unique not null, status varchar(20) not null, chat_table varchar(200) not null, `comment` varchar(100), constraint fli_fk_".$new_id." foreign key (friend_id) references users(id));";
        $result = mysqli_query($con, $query);
        if ($result) {
            echo "Success!";
        }
        else {
            $query = "delete from users where email like '".$email."';";
            mysqli_query($con, $query);
            echo "Inner database error.".$result;
        }
    }
    else {
        echo "Outer database error.";
    }
}
?>