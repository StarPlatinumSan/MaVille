# Stage 1: Build
FROM node:16-alpine AS builder

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
ARG VITE_API_URL
ENV VITE_API_URL=$VITE_API_URL
RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
COPY --from=builder /app/dist .

# Add a runtime environment configuration file
RUN echo "window.env = { VITE_API_URL: '${VITE_API_URL}' };" > ./env-config.js

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]





