<html>
<head>
    <title>Users</title>
</head>
<body>

<table>
    <thead>
        <tr>
            <th>id</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Birthday</th>
        </tr>
    </thead>
    <tbody>
        <#list users as user>
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.birthday}</td>
            </tr>
        </#list>
    </tbody>
</table>

</body>
</html>