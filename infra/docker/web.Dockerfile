FROM node:20-alpine

WORKDIR /app

# Copy only necessary files for production build
COPY web/package.json web/package-lock.json ./
COPY web/public ./public
COPY web/.next ./.next

# set node env for production so only dependencies install and ignore dev dependencies
ENV NODE_ENV=production

RUN npm ci

EXPOSE 3000

CMD ["npm", "start"]