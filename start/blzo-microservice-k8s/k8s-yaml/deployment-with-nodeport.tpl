---
apiVersion: v1
kind: Service
metadata:
  name: {PROJECT_NAME}-{APP_NAME}-nodeport
spec:
  type: NodePort
  ports:
    - port: {SERVER_PORT}
      nodePort: {NODE_PORT}
  selector:
    app: {PROJECT_NAME}-{APP_NAME}
---
apiVersion: v1
kind: Service
metadata:
  name: {PROJECT_NAME}-{APP_NAME}-service
spec:
  type: ClusterIP
  ports:
    - port: {SERVER_PORT}
      targetPort: {SERVER_PORT}
  selector:
    app: {PROJECT_NAME}-{APP_NAME}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {PROJECT_NAME}-{APP_NAME}-deployment
  labels:
    app: {PROJECT_NAME}-{APP_NAME}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: {PROJECT_NAME}-{APP_NAME}
  template:
    metadata:
      labels:
        app: {PROJECT_NAME}-{APP_NAME}
    spec:
      containers:
        - name: {PROJECT_NAME}-{APP_NAME}
          image: {REGISTRY_URL}/{PROJECT_NAME}/{APP_NAME}:{IMAGE_TAG}
          ports:
            - containerPort: {SERVER_PORT}
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: {CONFIG_ENV}
            - name: SEATA_CONFIG_ENV
              value: {CONFIG_ENV}