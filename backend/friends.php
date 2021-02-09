<?php
require "connection.php";
if (isset($_POST["f1"])) {
    $email = $_POST["email"];
    $query = "select * from `".$email."` where status like 'friend';";
    $result = mysqli_query($con, $query); 
    for ($i = 0; $i < mysqli_num_rows($result); $i++) {
        $row = mysqli_fetch_row($result);
        $id = $row[1];
        $relation = $row[2];
        $nikename = $row[4];
        
        $sub_query = "select email from users where id = ".$id.";";
        $sub_result = mysqli_query($con, $sub_query);
        $sub_row = mysqli_fetch_row($sub_result);
        $friend_email = $sub_row[0];
        
        echo $friend_email.";".$nikename.";".$relation."\r\n";
    }
}
else if (isset($_POST["f2"])) {
    $email = $_POST["email"];
    $key = $_POST["key"];
    
    $query = "select id from users where email like '%".$key."%' or username like '%".$key."%' union select friend_id from `".$email."` where comment like '%".$ksy."%';";
    $ids = mysqli_query($con, $query);
    
    for ($i = 0; $i < mysqli_num_rows($ids); $i++) {
        $id = mysqli_fetch_row($ids)[0];
        
        $name = "";
        $mail = "";
        $type = "unknown";
        
        $first_query = "select username, email from users where id = ".$id.";";
        $first_result = mysqli_query($con, $first_query);
        $first_row = mysqli_fetch_row($first_result);
        $name = $first_row[0];
        $mail = $first_row[1];
        
        $second_query = "select status, comment from `".$email."` where friend_id = ".$id.";";
        $second_result = mysqli_query($con, $second_query);
        if (mysqli_num_rows($second_result) > 0) {
            $second_row = mysqli_fetch_row($second_result);
            $type = second_row[0];
            $name = second_row[1];
        }
        
        if (strcmp($email, $mail) !== 0) {
            echo $mail.";".$name.";".$type."\r\n";
        }
    }
}
else if (isset($_POST["f3"])) {
    $email = $_POST["email"];
    
    $query = "select * from `".$email."` where status like 'inviting';";
    $result = mysqli_query($con, $query);
    if (mysqli_num_rows($result) > 0) {
        echo "On";
    }
    else {
        echo "Off";
    }
}
?>