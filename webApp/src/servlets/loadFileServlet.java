package servlets;


import Engine.EngineImpl;
import EngineManager.EngineManager;
import EngineUI.EngineUI;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import utilis.ServletUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet(urlPatterns = "/upload-file")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class loadFileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder fileContent = new StringBuilder();
        Collection<Part> parts = request.getParts();
        EngineImpl MainEngine=new EngineImpl();
       //ServletUtils.getMainEngine(getServletContext());
        //EngineImpl MainEngine=new EngineImpl();
        Gson gson = new Gson();
        String json="";
        for (Part part : parts) {
         MainEngine.loadXmlFile(part.getInputStream());
            String contestname = request.getParameter("contestname");
            contestname = contestname.trim();
            EngineManager manager=ServletUtils.getEngineManager(getServletContext());
            manager.addContest(MainEngine,contestname,MainEngine.getBattle().getBattleName());
       EngineUI DTO=MainEngine.CreateReturnObj();

             json = gson.toJson(DTO);
        //    out.println("size"+parts.size());
         //   printPart(part, out);

            //to write the content of the file to an actual file in the system (will be created at c:\samplefile)
         //   part.write("samplefile");


            //to write the content of the file to a string
            fileContent.append(readFromInputStream(part.getInputStream()));

        }
        out.write(json);
        //out.print(json);
        out.flush();
       // printFileContent(fileContent.toString(), out);
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }

    private void printFileContent(String content, PrintWriter out) {
        out.println("File content:");
        out.println(content);
    }

    private void printPart(Part part, PrintWriter out) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Parameter Name: ").append(part.getName()).append("\n")
                .append("Content Type (of the file): ").append(part.getContentType()).append("\n")
                .append("Size (of the file): ").append(part.getSize()).append("\n")
                .append("Part Headers:").append("\n");

        for (String header : part.getHeaderNames()) {
            sb.append(header).append(" : ").append(part.getHeader(header)).append("\n");
        }

        out.println(sb.toString());
    }
}



