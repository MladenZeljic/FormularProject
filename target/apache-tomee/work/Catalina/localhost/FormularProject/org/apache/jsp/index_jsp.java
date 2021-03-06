/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat (TomEE)/7.0.55 (1.7.1)
 * Generated at: 2018-11-30 13:48:06 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.project.models.Formular;
import com.project.dao.FormularDAO;
import com.project.models.Element;
import com.project.dao.ElementDAO;
import java.util.List;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
	int maxNumberOfRadioInputs = 5;
	int minNumberOfRadioInputs = 2;
	FormularDAO formularDao = new FormularDAO();
	
	List<Object> existingFormulars = formularDao.select();

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\t\t<link rel=\"shortcut icon\" href=\"\">\r\n");
      out.write("\t\t<title>A Web Page</title>\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<!-- Including bootstrap 3.3.7 css file -->\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"styles/index-style.css\">\r\n");
      out.write("\t\r\n");
      out.write("\t<body>\r\n");
      out.write("\t\t<div class=\"page\">\r\n");
      out.write("\t\t  \t<div id=\"tabs\" class=\"tabs\"> <!-- Container for current page tabs -->\r\n");
      out.write("\t\t      \t<a id=\"tab1\" href=\"javascript:void(0);\" class=\"tab-selected\" onclick=\"showSelectedView(this);\">\r\n");
      out.write("\t\t      \t\tAdministration\r\n");
      out.write("\t\t      \t</a><!--\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t--><a id=\"tab2\" href=\"javascript:void(0);\" onclick=\"showSelectedView(this);\">\r\n");
      out.write("\t\t      \t\tFormular\r\n");
      out.write("\t\t\t\t</a>\r\n");
      out.write("\t\t  \t</div>\r\n");
      out.write("\t\t  \t<div id=\"views\">\r\n");
      out.write("\t\t  \t\t<div id=\"tab1-view\" class=\"tab-view\">\r\n");
      out.write("\t\t  \t\t\t<form method=\"post\" onsubmit=\"return false;\" action=\"\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"form-group formular-form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-bar\">\r\n");
      out.write("        \t\t\t\t\t\t<div class=\"input-bar-item width100\">\r\n");
      out.write("        \t\t\t\t\t\t\t<label class=\"control-label formular-name-label\" for=\"formular-name-input\">Formular name:</label>\r\n");
      out.write("          \t\t\t\t\t\t\t<div class=\"input-group col-xs-3\">\r\n");
      out.write("                \t\t\t\t\t\t<input type=\"text\" class=\"form-control width100\" id=\"formular-name-input\" name=\"formular-name-input\" placeholder=\"Enter the name of formular\">\r\n");
      out.write("                \t\t\t\t\t\t<span class=\"input-group-btn\">\r\n");
      out.write("                  \t\t\t\t\t\t\t<button class=\"btn btn-info\" onclick=\"searchForFormulars()\">Search</button>\r\n");
      out.write("                \t\t\t\t\t\t</span>\r\n");
      out.write("            \t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t        \t</div>\r\n");
      out.write("      \t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t<form id=\"formular-form\" method=\"post\" class=\"formular-form form-hidden\" onsubmit=\"return false;\" action=\"\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"form-group col-xs-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<label class=\"control-label formular-element-label\" for=\"element1\">Element 1:</label>\r\n");
      out.write("\t          \t\t\t\t\t<div class=\"input-group\">\r\n");
      out.write("\t                \t\t\t\t<input type=\"text\" class=\"form-control width100\" id=\"element1\" name=\"element1\" placeholder=\"Enter a label\">\r\n");
      out.write("\t            \t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"form-group col-xs-2\">\r\n");
      out.write("                  \t\t\t\t<select class=\"form-control width100\" id=\"element1-selectType\" name =\"element1-selectType\" onchange=\"changeValidations(this); showRadioLabels(this)\">\r\n");
      out.write("                  \t\t\t\t\t<option value=\"Textbox\" selected>Textbox</option>\r\n");
      out.write("                  \t\t\t\t\t<option value=\"Checkbox\">Checkbox</option>\r\n");
      out.write("                  \t\t\t\t\t<option value=\"Radio buttons\">Radio buttons</option>\r\n");
      out.write("                  \t\t\t\t</select>\r\n");
      out.write("                  \t\t\t</div>\r\n");
      out.write("                  \t\t\t<div class=\"form-group col-xs-1 radio-number-hide\">\r\n");
      out.write("                  \t\t\t\t<select class=\"form-control width100\" id=\"element1-selectRadioNumber\" name =\"element1-selectRadioNumber\" onchange=\"changeRadioLabels(this)\">\r\n");
      out.write("                  \t\t\t\t\t");
 for (int i = 2; i <= maxNumberOfRadioInputs; i++){ 
      out.write("\r\n");
      out.write("                  \t\t\t\t\t\t<option value=\"");
      out.print(i );
      out.write("\" \r\n");
      out.write("                  \t\t\t\t\t\t\t");
 if(i == 2){ 
      out.write("\r\n");
      out.write("                  \t\t\t\t\t\t\t\tselected \r\n");
      out.write("                  \t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("                  \t\t\t\t\t\t\t>");
      out.print(i );
      out.write("\r\n");
      out.write("                  \t\t\t\t\t\t</option>\r\n");
      out.write("                  \t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("                  \t\t\t\t</select>\r\n");
      out.write("                  \t\t\t</div>\r\n");
      out.write("                  \t\t\t<div class=\"form-group col-xs-2\">\r\n");
      out.write("                  \t\t\t\t<select id=\"element1-selectValidation\" name =\"element1-selectValidation\" class=\"form-control width100\">\r\n");
      out.write("                  \t\t\t\t\t<option value=\"None\" selected>None</option>\r\n");
      out.write("                  \t\t\t\t\t<option value=\"Mandatory\">Mandatory</option>\r\n");
      out.write("                  \t\t\t\t\t<option value=\"Numeric\">Numeric</option>\r\n");
      out.write("                  \t\t\t\t\t<option value=\"Email\">Email</option>\r\n");
      out.write("                  \t\t\t\t\t<option value=\"Date\">Date</option>\r\n");
      out.write("                  \t\t\t\t</select>\r\n");
      out.write("                  \t\t\t</div>\r\n");
      out.write("                  \t\t\t<div class=\"form-group btn-group\">\r\n");
      out.write("                  \t\t\t\t<button type=\"button\" class=\"btn btn-add-element\" onclick=\"addElement()\">&#43;</button>\r\n");
      out.write("                \t\t\t</div>\r\n");
      out.write("                \t\t\t<div class=\"radio-input-group radio-input-hidden\">\r\n");
      out.write("\t                \t\t\t<div class=\"form-group col-xs-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t                \t\t\t<div class=\"form-group col-xs-4 radio-input\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"input-group\">\r\n");
      out.write("\t\t                \t\t\t\t<input type=\"text\" class=\"form-control width100\" id=\"radioelement1-labelinput1\" name=\"radioelement1-labelinput1\" placeholder=\"Enter a label\">\r\n");
      out.write("\t\t            \t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("                \t\t\t<div class=\"radio-input-group radio-input-hidden\">\r\n");
      out.write("\t                \t\t\t<div class=\"form-group col-xs-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t                \t\t\t<div class=\"form-group col-xs-4 radio-input\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"input-group\">\r\n");
      out.write("\t\t                \t\t\t\t<input type=\"text\" class=\"form-control width100\" id=\"radioelement1-labelinput2\" name=\"radioelement1-labelinput2\" placeholder=\"Enter a label\">\r\n");
      out.write("\t\t            \t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("                  \t\t</div>\r\n");
      out.write("                  \t\t<div class=\"flex\"></div>\r\n");
      out.write("                  \t\t<div class=\"form-group\">\r\n");
      out.write("                  \t\t\t<button class=\"btn btn-info btn-save-formular\" onclick=\"submitFormular()\">Save</button>\t\t\t\r\n");
      out.write("                  \t\t</div>\r\n");
      out.write("           \r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div id=\"tab2-view\" class=\"tab-view-hide\">\r\n");
      out.write("\t\t\t\t\t<form method=\"post\" onsubmit=\"return false;\" action=\"\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row formular-form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"form-group col-xs-3\">\r\n");
      out.write("    \t\t\t\t\t\t\t<label for=\"sort\" class=\"control-label formular-select-label\">Formular name:</label>\r\n");
      out.write("    \t\t\t\t\t\t\t\t<div class=\"input-group formular-select-group\">\r\n");
      out.write("    \t\t\t\t\t\t\t\t\t<select class=\"form-control\" id=\"formular-name-select\" name=\"formular-name-select\">\r\n");
      out.write("                \t\t\t\t\t\t\t");
 for(Object objectFormular : existingFormulars){
                							Formular formular = (Formular)objectFormular;
                							
      out.write("\r\n");
      out.write("                \t\t\t\t\t\t\t<option value=\"");
      out.print(formular.getFormularId());
      out.write('"');
      out.write('>');
      out.print(formular.getFormularName());
      out.write("</option>\r\n");
      out.write("                \t\t\t\t\t\t\t");

                							}
                						
                						
      out.write("\r\n");
      out.write("                \t\t\t\t\t\t</select>\r\n");
      out.write("        \r\n");
      out.write("     \t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"form-group formular-version-group\">\r\n");
      out.write("                  \t\t\t\t<label class=\"control-label formular-version-label\" for=\"formular-version-input\">Version:</label>\r\n");
      out.write("\t          \t\t\t\t\t<div class=\"input-group formular-version-input-group\">\r\n");
      out.write("\t                \t\t\t\t<input type=\"text\" class=\"form-control width100\" id=\"formular-version-input\" name=\"formular-version-input\" placeholder=\"Version\">\r\n");
      out.write("\t            \t\t\t\t</div>\r\n");
      out.write("                  \t\t\t</div>\r\n");
      out.write("                  \t\t\t\r\n");
      out.write("                  \t\t\t<div class=\"form-group btn-group\">\r\n");
      out.write("                \t  \t\t\t<button class=\"btn btn-info\" onclick=\"searchFormular()\">Load</button>\t\t\t\r\n");
      out.write("           \t\t       \t\t</div>\r\n");
      out.write("                  \t\t</div>\r\n");
      out.write("      \t\t\t\t\t\r\n");
      out.write("           \t\t\t</form>\r\n");
      out.write("\t\t\t\t\t<form method=\"post\" class=\"formular-form form-hidden\" onsubmit=\"return false;\" action=\"\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("                  \t\t<div class=\"flex\"></div>\r\n");
      out.write("                  \t\t<div class=\"form-group\">\r\n");
      out.write("                  \t\t\t<div class=\"btn btn-group btn-delete-formular\">                  \t\t\t\r\n");
      out.write("                  \t\t\t\t<button class=\"btn btn-danger\" onclick=\"deleteFormular()\">Delete</button>\t\t\t\r\n");
      out.write("                  \t\t\t</div>\r\n");
      out.write("                  \t\t\t\r\n");
      out.write("                  \t\t\t<div class=\"btn btn-group btn-save-formular\">\r\n");
      out.write("                  \t\t\t\t<button class=\"btn btn-info\" onclick=\"saveFormular()\">Save</button>\t\t\t\r\n");
      out.write("                  \t\t\t</div>\r\n");
      out.write("                  \t\t\t\r\n");
      out.write("                  \t\t</div>\t\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<script src=\"scripts/js/administration-script.js\"></script>\r\n");
      out.write("\t\t<script src=\"scripts/js/formular-script.js\"></script>\r\n");
      out.write("\t\t<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n");
      out.write("\t\t<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\r\n");
      out.write("\t</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
