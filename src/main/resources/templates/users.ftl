<!DOCTYPE html>
<html>
    <#include "bootstrap-header.ftl">
    <body>
        <main role="main" class="container">
          <div class="container">
             <h2>List of users</h2>
            <table border="1">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>UserName</th>
                            <th>City</th>
                            <th>Date Of Joining</th>
                            <th>Exact Date of Birth</th>
                            <th>Status</th>
                            <th>Description</th>

                        <#list users as u>
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.name}</td>
                                <td>${u.userName}</td>
                                <td>${u.city?upper_case}</td>
                                <td>${u.dateOfJoining}</td>
                                <td>${u.exactDob}</td>
                                <td>${u.status}</td>
                                <td>${u.description?cap_first}</td>
                            </tr>
                        </#list>
                    </table>
          </div>
        </main>
      </body>
</html>