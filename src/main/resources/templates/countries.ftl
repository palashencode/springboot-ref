<!DOCTYPE html>
<html>
    <#include "bootstrap-header.ftl">
    <body>
        <main role="main" class="container">
          <div class="starter-template">
             <h2>List of countries</h2>
            <table border="1">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>

                        <#list countries as c>
                            <tr>
                                <td>${c.id}</td>
                                <td>${c.name}</td>
                            </tr>
                        </#list>
                    </table>
          </div>
        </main>
      </body>
</html>