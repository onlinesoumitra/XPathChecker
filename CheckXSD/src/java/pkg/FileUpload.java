/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.namespace.NamespaceContext;

import org.w3c.dom.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.http.HttpSession;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FileUpload extends HttpServlet {

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
//private String Format="StandardXPath";
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            ArrayList nodeCount = new ArrayList();
            ArrayList nodeValue = new ArrayList();
            int CountMatch = 0;
            int CountMismatch = 0;
           
            String xpathExists = "";
            HttpSession session = req.getSession(true);
            String xpaths = req.getParameter("xpath");
            System.out.print("xpaths:"+xpaths);
            String valuSelectIndicator = req.getParameter("group1");
            // Format = req.getParameter("group2");
            System.out.print("valuSelectIndicator:" + valuSelectIndicator+req.getParameter("group2")+"xxx");
            //System.out.print("xpaths..........:"+xpaths);
            Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
            BlobKey blobKey = blobs.get("myFile");
            InputStream is = new BlobstoreInputStream(blobKey);
            //InputStream in = /* your InputStream */;
            //InputStream in = /* your InputStream */;
            InputStreamReader instrm = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(instrm);
            String read = "";
            String str = "";
            //System.out.print(read);
            while ((read = br.readLine()) != null) {
                // System.out.println("read data1:"+read);
                str = str + read;
                // System.out.println("read data:"+read);
            }
            //  System.out.println("read data.........................:"+str);
            //gettting xpaths in an array

            ArrayList<String> ar_xpath = new ArrayList();
            ArrayList<String> actual_ar_xpath = new ArrayList();
            StringTokenizer stringTokenizer = new StringTokenizer(xpaths, "\n");
            //System.out.println("The total no. of tokens generated :  " + stringTokenizer.countTokens());
            while (stringTokenizer.hasMoreTokens()) {

                ar_xpath.add(stringTokenizer.nextToken());
                //System.out.println(stringTokenizer.nextToken());
            }
System.out.print("size of ar_xpath"+ar_xpath.size());
            //Forming xapths in proper format
            for (int k = 0; k < ar_xpath.size(); k++) {

               String Rawxpath = ar_xpath.get(k);
               //  actual_ar_xpath.add(Rawxpath.trim());
              //  System.out.print("Rawxpath" + Rawxpath + "," + Format);
                if (req.getParameter("group2").equals("StandardXPath")) {
                    actual_ar_xpath.add(Rawxpath.trim());
                    System.out.print("Rawxpath" + Rawxpath);
                } else {
                    if (!Rawxpath.contains(".@")) {

                        Rawxpath = Rawxpath.replace(".", "/");
                        Rawxpath = Rawxpath.concat("/text()");
                        Rawxpath = "//".concat(Rawxpath);
                        actual_ar_xpath.add(Rawxpath);
                        // System.out.print(Rawxpath);
                    } else {
                        Rawxpath = Rawxpath.replace(".@", "[@");
                       Rawxpath = Rawxpath.concat("]/*/text()");
                        Rawxpath = "//".concat(Rawxpath);
                       Rawxpath = Rawxpath.replace(".", "/");
                        System.out.print(Rawxpath+"\n");
                       actual_ar_xpath.add(Rawxpath);
                    }
               }

            }
System.out.println(actual_ar_xpath);
            ArrayList<String> xpathCheck = new ArrayList();
//parsing xml and matching xpaths
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            InputSource inputsource = new InputSource(new StringReader(str));
            Document doc = builder.parse(inputsource);
            XPath xpath = XPathFactory.newInstance().newXPath();
            String vv = "";
            XPathExpression expr = null;
            xpath.setNamespaceContext((NamespaceContext) new UniversalNamespaceResolver(doc));
            for (int j = 0; j < actual_ar_xpath.size(); j++) {
                //XPathExpression expr = xpath.compile("//person/*/text()");
                try {
                    expr = xpath.compile(actual_ar_xpath.get(j));

                } catch (Exception e) {
                    session.setAttribute("errormessage", "Error In the XPath :" + ar_xpath.get(j));
                    res.sendRedirect("/Error.jsp");

                }
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                if (nodes.getLength() > 0) {
                    System.out.println(actual_ar_xpath.get(j) + "xpath exists");
                    xpathCheck.add("xpath exists");
                    CountMatch++;
                    String strNodeVal = "";
                    String comma = ",";
                    for (int i = 0; i < nodes.getLength(); i++) {
                        // System.out.println(nodes.item(i).getNodeValue());
                        //  nodeCount.add(nodes.getLength());
                        strNodeVal = nodes.item(i).getNodeValue() + "," + strNodeVal;


                        // nodeValue.add(nodes.item(0).getNodeValue());

                    }

                    if (valuSelectIndicator.equals("First")) {
                        nodeValue.add(nodes.item(0).getNodeValue());
                        //System.out.println("nnnn");
                    } else if (valuSelectIndicator.equals("All")) {
                        nodeValue.add(strNodeVal);
                        //  System.out.println("nnnn");
                    }

                } else {
                    System.out.println(actual_ar_xpath.get(j) + "xpath doesn't exists");
                    xpathCheck.add("xpath doesn't exists");
                    nodeValue.add("---");
                    CountMismatch++;
                    //  nodeCount.add(0);
                }


            }
            nodeCount.add(111);
            req.setAttribute("message", xpathExists);
            session.setAttribute("nodeValue", nodeValue);
            session.setAttribute("CountMismatch", CountMismatch);
            session.setAttribute("CountMatch", CountMatch);
            session.setAttribute("XPaths", ar_xpath);
            session.setAttribute("CkeckResult", xpathCheck);
            session.setAttribute("theName", "XPath Checking Report");
            //req.getRequestDispatcher("results.jsp").forward(req, res);
            if (blobKey == null) {
                res.sendRedirect("/");
            } else {
                // res.sendRedirect("/Serve?blob-key=" + blobKey.getKeyString());
                res.sendRedirect("/results.jsp");
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            //session.setAttribute("errormessage", "XPathExpressionException In the XPath :");
            res.sendRedirect("/Error.jsp");
        } catch (SAXException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            //session.setAttribute("errormessage", "SAXException in xml");
            res.sendRedirect("/Error.jsp");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            // session.setAttribute("errormessage", "ParserConfigurationException in the xml" );
            res.sendRedirect("/Error.jsp");
        }
    }
}
