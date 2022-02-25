# el code
# https://towardsdatascience.com/head-pose-estimation-using-python-d165d3541600
# https://medium.com/analytics-vidhya/real-time-head-pose-estimation-with-opencv-and-dlib-e8dc10d62078
# https://learnopencv.com/head-pose-estimation-using-opencv-and-dlib/
import cv2
import mediapipe as mp
import numpy as np
import matplotlib.pyplot as plt #para construir la grafica






mp_face_mesh = mp.solutions.face_mesh
face_mesh = mp_face_mesh.FaceMesh(min_detection_confidence=0.5, min_tracking_confidence=0.5)
cap = cv2.VideoCapture(0)

#declaracion de la grafica
#plt.ion() # decimos de forma explícita que sea interactivo
plt.title("Atencion al rededor del tiempo",fontsize=25)
plt.ylabel("No/Si",fontsize=18)
plt.ylim(0,1)
y_data = [] # los datos que vamos a dibujar y a actualizar


while cap.isOpened():
    success, image = cap.read()

    image = cv2.cvtColor(cv2.flip(image, 1), cv2.COLOR_BGR2RGB)

    image.flags.writeable = False
    
    results = face_mesh.process(image)
    
    image.flags.writeable = True
    
    image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)

    img_h, img_w, img_c = image.shape
    face_3d = []
    face_2d = []

    if results.multi_face_landmarks:
        for face_landmarks in results.multi_face_landmarks:
            for idx, lm in enumerate(face_landmarks.landmark):
                if idx == 33 or idx == 263 or idx == 1 or idx == 61 or idx == 291 or idx == 199:
                    if idx == 1:
                        nose_2d = (lm.x * img_w, lm.y * img_h)
                        nose_3d = (lm.x * img_w, lm.y * img_h, lm.z * 8000)

                    x, y = int(lm.x * img_w), int(lm.y * img_h)

                    # cogemos las coordenadas 2d
                    face_2d.append([x, y])

                    # cogemos las coordenadas 3d
                    face_3d.append([x, y, lm.z])       
            
            # convertimos a un array de NumPy 
            face_2d = np.array(face_2d, dtype=np.float64)

            # convertimos a un array de NumPy
            face_3d = np.array(face_3d, dtype=np.float64)

            
            focal_length = 1 * img_w

            cam_matrix = np.array([[focal_length, 0, img_h / 2],[0, focal_length, img_w / 2],[0, 0, 1]])

            # la matriz de distancia
            dist_matrix = np.zeros((4, 1), dtype=np.float64)

            # Solve PnP
            success, rot_vec, trans_vec = cv2.solvePnP(face_3d, face_2d, cam_matrix, dist_matrix)

            # cogemos la matriz de rotacion
            rmat, jac = cv2.Rodrigues(rot_vec)

            # cogemos los angulos
            angles, mtxR, mtxQ, Qx, Qy, Qz = cv2.RQDecomp3x3(rmat)

            # obtenemso las coordenadas x y de rotacion
            x = angles[0] * 360
            y = angles[1] * 360

            ok=False
            # seleccion
            if y < -10:
                y_data.append(0)
                text = "No atiende"
            elif y > 10:
                y_data.append(0)
                text = "No atiende"
            elif x < -10:
                y_data.append(0)
                text = "No atiende"
            elif y > 10:
                y_data.append(0)
                text = "No atiende"
            else:
                y_data.append(1)
                text = "Atendiendo"
                ok=True

            # palo de la nariz
            nose_3d_projection, jacobian = cv2.projectPoints(nose_3d, rot_vec, trans_vec, cam_matrix, dist_matrix)

            p1 = (int(nose_2d[0]), int(nose_2d[1]))
            p2 = (int(nose_3d_projection[0][0][0]), int(nose_3d_projection[0][0][1]))
            
            cv2.line(image, p1, p2, (255, 0, 0), 2)

            #construccion del grafioc
            if len(y_data) <= 100:
                plt.plot(y_data)
            else:
                plt.plot(y_data[-100:])
            plt.pause(0.01)
            plt.cla() # esto limpia la información del axis (el área blanca donde
              # se pintan las cosas.

            # texto de la imagen
            if ok:
                cv2.putText(image, text, (20, 20), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
            else:
                cv2.putText(image, text, (20, 20), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)

    cv2.imshow('Control de atencion', image)

    if cv2.waitKey(5) & 0xFF == 27:
        break

cap.release()