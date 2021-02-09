<?php
require "connection.php";
if (isset($_POST["i1"])) {
    $email = $_POST["email"];
    $otherEmail = $_POST["otheremail"];
    $comment = $_POST["comment"];
    
    $first_query = "select id from users where email like'".$email."';";
    $first_result = mysqli_query($con, $first_query);
    $this_id = mysqli_fetch_row($first_result)[0];
    
    $second_query = "select id from users where email like '".$otherEmail.";";
    $second_result = mysqli_query($con, $second_query);
    $other_id = mysqli_fetch_row($second_result)[0];
    
    $query = "update `".$email."` set status = 'friend', comment = '".comment"' where friend_id = ".$other_id.";";
    mysqli_query($con, $query);
    $query = "update `".$otherEmail."` set status = 'friend' where friend_id = ".$this_id.";";
    mysqli_query($con, $query);
    $query = "select chat_table from `".$email."` where friend_id = ".$other_id.";";
    $table = mysqli_fetch_row(mysqli_query($con, $query))[0];
    $query = "create table ".$table." (id int primary key auto_increment not null, from_this int not null, message text not null);";
    mysqli_query($con, $query);
}
else if (isset($_POST["i2"])) {
    $email = $_POST["email"];
    $otherEmail = $_POST["otheremail"];
    
    $first_query = "select id from users where email like'".$email."';";
    $first_result = mysqli_query($con, $first_query);
    $this_id = mysqli_fetch_row($first_result)[0];
    
    $second_query = "select id from users where email like '".$otherEmail.";";
    $second_result = mysqli_query($con, $second_query);
    $other_id = mysqli_fetch_row($second_result)[0];
    
    $query = "delete from `".$email."` where friend_id = ".$other_id.";";
    mysqli_query($con, $query);
    $query = "delete from `".$otherEmail."` where friend_id = ".$this_id.";";
    mysqli_query($con, $query);
}
else if (isset($_POST["i3"])) {
    $email = $_POST["email"];
    $otherEmail = $_POST["otheremail"];
    $comment = $_POST["comment"];
    
    $first_query = "select id from users where email like'".$email."';";
    $first_result = mysqli_query($con, $first_query);
    $this_id = mysqli_fetch_row($first_result)[0];
    
    $second_query = "select id from users where email like '".$otherEmail.";";
    $second_result = mysqli_query($con, $second_query);
    $other_id = mysqli_fetch_row($second_result)[0];
    
    $chattable = "and".$this_id."and".$other_id;
    
    $query = "insert `".$email."` (friend_id, status, chat_table, comment) values(".$other_id.", 'pending', '".$chattable."', '".$comment"');";
    mysqli_query($con, $query);
    $query = "insert `".$otheremail."` (friend_id, status, chat_table) values(".this_id.", 'inviting', '".$chattable."');";
    mysqli_query($con, $query);
}
?>