import cv2
import os 
import imutils

face_cascade= cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
eye_cascade = cv2.CascadeClassifier('haarcascade_eye.xml')


personName = 'Oscar'
personName2 = 'Alejandro'
personName3 = ' Marcos'
personName4 = 'Fran'
personName5 = 'Javier'
dataPath = '/home/unioscar/Escritorio/dei/practica2'
personPath = dataPath + '/' + personName5


if not os.path.exists(personPath):
    print ('Carpeta creada: ', personPath)
    os.makedirs(personPath)

count = 0

cap = cv2.VideoCapture(0)
#sal = cv2.VideoWriter('videoSalidaOscar.mp4', cv2.VideoWriter_fourcc(*'XVID'),20.0,(640,480))

while True:
    _, img = cap.read()
    img = imutils.resize(img, width=640)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    auxImg = img.copy()

    faces = face_cascade.detectMultiScale(gray, 1.1, 4)

    for(x, y, w, h) in faces:
        cv2.rectangle(img, (x,y), (x+w, y+h), (0, 255, 0), 2)
        roi_gray = gray[y:y+w, x:x+w]
        roi_color = img[y:y+w,x:x+h]
        eyes = eye_cascade.detectMultiScale(roi_gray, 1.3, 4)
       # for (ex,ey,ew,eh) in eyes:
       #     cv2.rectangle(roi_color, (ex,ey),(ex + ew, ey + eh), (255, 0, 0))

        roi_gray = cv2.resize(roi_gray,(150,150),interpolation=cv2.INTER_CUBIC)
        cv2.imwrite(personPath + '/rostro_{}.jpg'.format(count),roi_gray)
        count = count + 1
        print(count)
    #sal.write(img)
    cv2.imshow('img',img)
    k = cv2.waitKey(30)
    if k == 27:
        break
    if count >= 300:
        break

#sal.release()
cap.release()