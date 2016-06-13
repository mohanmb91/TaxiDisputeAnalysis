<?php

$servername = "mysql3.gear.host";
$username = "applogindb";
$password = "Di78B?!Qu5ou";
$dbname="applogindb";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql = "select * from bigdata";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
        while($row1 = $result->fetch_assoc()) {
            $lat_long_array[] = $row1;
        }
    } else {
        echo "0 results";
    }

echo json_encode($lat_long_array);


?>