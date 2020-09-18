<!DOCTYPE html>
<html>
<head>
    <title>Jenkins notification</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            background-color: #f6f6f6;
            -webkit-font-smoothing: antialiased;
            font-size: 14px;
            line-height: 1.4;
            -ms-text-size-adjust: 100%;
            -webkit-text-size-adjust: 100%; 
        }    
        .successful-header{
            background-color: #DFF0D8 !important;
            color: #417A42;
            padding: 20px;
            font-size: 18px;
            text-align: left;
            border: 2px solid #D6E9C6;
            border-radius: 5px;
        }

        .warning-header {
            background-color: #FCF8E3 !important;
            color: #8A6D3B;
            padding: 20px;
            font-size: 18px;
            text-align: left;
            border: 2px solid #FAEED1;
            border-radius: 5px;          
        }

        .failed-header {
            background-color: #F2DEDE !important;
            color: #A94442;
            padding: 20px;
            font-size: 18px;
            text-align: left;
            border: 2px solid #EBCCD1;
            border-radius: 5px;
        }   

        .info-header {
            background-color: #D9EDF7 !important;
            color: #5188A3;
            padding: 20px;
            font-size: 18px;
            text-align: left;
            border: 2px solid #BCE8F1;
            border-radius: 5px;            
        }              




        .header-table{
            background: #ffffff;
            width:60%; 
            margin-left:20%; 
            margin-right:20%;
        }



        .details-table{
            width:60%; 
            margin-left:20%; 
            margin-right:20%;
        }

        .details-message{
            padding: 20px;
            font-size: 14px;
            text-align: left;
            border: 1px solid #DDDDDD;
            border-radius: 5px;
        }



        .content-block {
            padding-bottom: 10px;
            padding-top: 10px;
        }  

        .powered-by a {
            text-decoration: none; 
        } 



        .footer {
            clear: both;
            text-align: center;
            margin-top: 10px;
            text-align: center;
            width: 100%; 
        }

        .footer-table{
            width:60%; 
            margin-left:20%; 
            margin-right:20%;
        }


        .footer p,
        .footer span {
            color: #999999;
            font-size: 12px;
            text-align: center; 
        }   
        .footer a {
            color: #7FA3E3;
            font-size: 12px;
            text-align: center; 
        }          

    </style>

</head>
<body>

    <table class="header-table">
        <tr>
            <td class="failed-header"> 
                <strong>Failure!</strong> Pipeline finished with errors.
            </td>
        </tr>
    </table>

    <table class="details-table">
        <tr>
            <td class="details-message">
                <p><strong>Application:</strong> {APP_NAME}</p>
                <p><strong>Job:</strong> {JOB_NAME}</p>
                <p><strong>Branch:</strong> {BRANCH_NAME}</p>
                <p>Click <a href="{BUILD_URL}">Classic Console</a> for build details.</p>   
                <p>Click <a href="{RUN_DISPLAY_URL}">Blue Ocean Console</a> for build details.</p> 
            </td>
         </tr>
    </table>

    <div class="footer">
        <table class="footer-table">
            <tr>
                <td class="content-block powered-by">
                    <p>{COMPANY_NAME}</p>
                    <p>Powered by <a href="{JENKINS_URL}">Jenkins</a>.</p>
                </td>
            </tr>
        </table>
    </div>
  
</body>
</html>
