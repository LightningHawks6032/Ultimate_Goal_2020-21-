package org.firstinspires.ftc.teamcode.debug;

import org.firstinspires.ftc.teamcode.RobotPos;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class JavaHTTPServer {
    static final int PORT = 8080;
    static ServerSocket serverConnect;

    static final byte[] indexHTMLData = ("<div style=\"font-family: monospace;width: 360;color: #888888;text-align: center;transform: scaleX(1.5);\">-- FRONT -- FRONT -- FRONT --</div><canvas id=\"cnv\" width=\"360\" height=\"720\"></canvas><script defer>(async()=>{ var ctx = cnv.getContext(\"2d\"); var fetchPaths = async () => { var body = await fetch(window.location.href+\"path\",{mode:\"no-cors\"}).then(res=>res.body); var reader = body.getReader(), chunks = []; while (chunks.length == 0 || !chunks[chunks.length-1].done) chunks.push(await reader.read()); var l = 0; for (var c of chunks) if (c.value) l += c.value.length; var buf=new ArrayBuffer(l), dv = new DataView(buf), i = 0; for (var c of chunks) if (c.value) for (var ui8 of c.value) dv.setUint8(i++,ui8); var n = dv.getInt32(0),unpacked = []; for (var i = 4; i < l; i+=4*n*3) { unpacked.push([]); for (var j = 0; j < n; j++) unpacked[unpacked.length-1].push({x:dv.getFloat32(i+j*4*3+0),y:dv.getFloat32(i+j*4*3+4),r:dv.getFloat32(i+j*4*3+8)});} var reshaped = new Array(n).fill().map(_=>[]); for (var i = 0; i < unpacked.length; i++) for (var j = 0; j < n; j++) reshaped[j][i] = unpacked[i][j]; return reshaped;}; var drawFrame = async () => {var paths = await fetchPaths(), i = 0, cols = [\"#fc8f41\",\"#4ec3e6\",\"#4ee699\"]; ctx.resetTransform(); ctx.clearRect(0,0,720,1440);var s=5; ctx.setTransform(s,0,0,s,48*s,72*s); ctx.strokeStyle=\"#dddddd\";for (var j = -3; j <= 3; j++) {ctx.lineWidth=j==0?1:0.3;ctx.beginPath();ctx.moveTo(-72,j*24);ctx.lineTo(72,j*24);ctx.moveTo(j*24,-72);ctx.lineTo(j*24,72);ctx.stroke();ctx.closePath();};ctx.lineWidth=1; for (var path of paths) { ctx.beginPath(); ctx.strokeStyle = cols[i++]; var lpt = NaN;console.log(\"S\"); for (var pt of path) {console.log(pt.x,isFinite(pt.x));if (isFinite(pt.x)) {if (isFinite(lpt)) ctx.lineTo(pt.x,pt.y); else ctx.moveTo(pt.x,pt.y);}; lpt=pt.x;}; ctx.stroke(); ctx.closePath(); }}; setInterval(drawFrame,500); })();</script>").getBytes();

    public static void init() {
        try {
            serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }
    public static void update() {
        try {
            serverConnect.setSoTimeout(10);
            Socket s = serverConnect.accept();
            dealWithClient(s);
        } catch (SocketTimeoutException ignored) {
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }
    public static void close() {
        try {
            serverConnect.close();
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    public static void handle(Socket s, String method, String path, Map<String, String> headers, byte[] body) throws IOException {

        Map<String,String> resHeaders = new HashMap<>();

        if (method.equals("GET") && path.equals("/")) {
            resHeaders.put("Content-Type","text/html");
            respond(s, 200, resHeaders, indexHTMLData, false);
        } else if (method.equals("GET") && path.equals("/path"))
            respond(s,200,resHeaders,getData(),false);
        else
            respond(s,404, headers, "404 not found lol".getBytes(), false);

    }

    public static void respond(Socket s, int status, Map<String, String> headers, byte[] body, boolean keepAlive) throws IOException {
        OutputStream outputStream = s.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream);
        BufferedOutputStream dataOut = new BufferedOutputStream(outputStream);

        if (body != null) headers.put("Content-Length", String.valueOf(body.length));

        out.println("HTTP/1.1 "+status);
        for (String hdrName : headers.keySet())
            out.println(hdrName+": "+headers.get(hdrName));
        out.println();
        out.flush();

        if (body != null) {
            dataOut.write(body);
            dataOut.flush();
        }

        if (!keepAlive) s.close();
    }

    private static void dealWithClient(Socket connect) {
        // we manage our particular client connection
        BufferedReader in = null;

        //noinspection RedundantSuppression
        try {
            // we read characters from the client via input stream on the socket
            InputStream is = connect.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));

            // get first line of the request from the client
            String input = in.readLine();
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase();
            String reqPath = parse.nextToken().toLowerCase();

            Map<String, String> headers = new HashMap<>();
            while(true) {
                String ln = in.readLine().trim();
                if (ln.isEmpty()) break;
                int kvs = ln.indexOf(":");
                headers.put(ln.substring(0,kvs).trim(),ln.substring(kvs+1).trim());
            }
            byte[] body = new byte[0];//new byte[Integer.parseInt(headers.get("Content-Length"))];

            //noinspection ResultOfMethodCallIgnored
            //is.read(body);


            handle(connect,method,reqPath,headers,body);

        } catch (IOException ioe) {
            System.err.println("Server error : " + ioe);
        } finally {
            try {
                if (in != null) in.close();
                connect.close(); // we close socket connection
            } catch (IOException e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }
        }
    }


    public static List<RobotPos[]> pathData = new ArrayList<>();
    public static final int pathDataNElements = 2;
    private static byte[] getData() {
        byte[] bytes = new byte[Integer.SIZE/8+pathData.size()*Float.SIZE/8*pathDataNElements*3];
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.putInt(pathDataNElements);
        for (RobotPos[] pathElt : pathData)
            for (RobotPos pos : pathElt) {
                if (pos == null) {
                    for(int j=0;j<3;j++)bb.putFloat(Float.NaN);
                } else {
                    bb.putFloat((float) pos.x);
                    bb.putFloat((float) pos.y);
                    bb.putFloat((float) pos.r);
                }
            }
        return bytes;
    }
}