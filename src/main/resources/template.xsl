<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="/">
    <html>
      <body>
        <h2>Company Clients</h2>
        <table border="1">
          <tr>
            <th>Identification Number</th>
            <th>ID Type</th>
            <th>Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Birthdate</th>
            <th>Creation Date</th>
            <th>Modification Date</th>
            <th>Money Invested</th>
            <th>Percentage Invested</th>
          </tr>
          <tr>
            <td>987</td>
            <td>CC</td>
            <td>Andrea</td>
            <td>Jordan</td>
            <td>andrea@gmail.com</td>
            <td>1972-10-31</td>
            <td>2024-05-21</td>
            <td>2024-05-21</td>
            <td>1000000.0</td>
            <td>16.666666666666664</td>
          </tr>
          <tr>
            <td>654</td>
            <td>CC</td>
            <td>Emmanuel</td>
            <td>Montes</td>
            <td>emmanuel@gmail.com</td>
            <td>1972-10-31</td>
            <td>2024-05-21</td>
            <td>2024-05-21</td>
            <td>5000000.0</td>
            <td>83.33333333333334</td>
          </tr>
        </table>
        <p>Total Money Invested: 6000000.0</p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
