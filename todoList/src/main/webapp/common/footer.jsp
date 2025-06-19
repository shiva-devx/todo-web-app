<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
.footer {
    position: fixed;
    bottom: 0;
    width:100%;
    height: 40px;
    background-color: tomato;
}

</style>

<footer class="footer font-small black" style= "background-color: #CDC1FF">
    <!-- Copyright -->
  <div class="footer-copyright text-center py-3">  &copy; <%= java.time.Year.now().getValue() %>
      </div>
</footer>
<!-- Footer -->