<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!Doctype html>
<html>
<head>
    <title>MailGun Demo Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/bootstrap-responsive.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/bootstrap-tagsinput.css"/>" rel="stylesheet">

    <script>
        $('.to').tagsinput();
    </script>
</head>
<body>
<div class="col-sm-12 container">
    <div class="content">
        <form action="sendEmail" method="post" class="form-horizontal col-sm-8 col-sm-offset-2"
              enctype="multipart/form-data">

            <div class="form-group">
                <h2>Send Mail Demo Form</h2>
                <hr/>
            </div>

            <c:if test="${error != null}">
                <div class="form-group">
                    <div class="alert alert-warning">
                        Failed to Send Email:
                        <br>
                        Reason: <c:out value="${error}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${msg != null}">
                <div class="form-group">
                    <div class="alert alert-info">
                        <c:out value="${msg}"/>
                    </div>
                </div>
            </c:if>

            <div class="form-group">
                <label for="from" class="col-sm-3">Form</label>

                <div class="col-lg-9">
                    <input id="from" name="from" class="form-control" type="email">
                </div>
            </div>
            <div class="form-group">
                <label for="to" class="col-sm-3">To</label>

                <div class="col-lg-9">
                    <input id="to" name="to" required class="form-control" type="email" multiple >
                </div>
            </div>
            <div class="form-group">
                <label for="cc" class="col-sm-3">CC</label>

                <div class="col-lg-9">
                    <input id="cc" name="cc" required class="form-control" type="email" multiple >
                </div>
            </div>
            <div class="form-group">
                <label for="bcc" class="col-sm-3">BCC</label>

                <div class="col-lg-9">
                    <input id="bcc" name="bcc" required class="form-control" type="email" multiple >
                </div>
            </div>
            <div class="form-group">
                <label for="subject" class="col-sm-3">Subject</label>

                <div class="col-lg-9">
                    <input id="subject" name="subject" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="content" class="col-sm-3">Content</label>

                <div class="col-lg-9">
                    <textarea id="content" name="content" required class="form-control" rows="6"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="attachment" class="col-sm-3">Attachment</label>

                <div class="col-lg-9"><input id="attachment" name="attachment" class="form-control" type="file" multiple>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3"></label>

                <div class="col-lg-9"><input id="submit" type="submit" value="Send a Email"
                                             class="btn-sm btn-primary ">
                    <input id="reset" type="reset" value="Reset"
                           class="btn-sm btn-default">
                </div>
            </div>
        </form>
    </div>
</div>

<script src="<c:url value="/resources/js/jquery-1.8.2.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-tagsinput.js" />"></script>
</body>
</html>