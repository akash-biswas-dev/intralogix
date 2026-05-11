# -------- 1. Install dependencies --------
FROM node:20-alpine AS builder


WORKDIR /app

COPY web/ .

# Install dependencies based on lock file
RUN npm ci

# -------- 3. Production image --------
FROM node:20-alpine AS runner
WORKDIR /app

ENV NODE_ENV=production

COPY web/package.json web/package-lock.json ./
COPY web/public ./public

RUN npm ci

# Only copy necessary files
COPY --from=builder /app/.next ./.next

EXPOSE 3000

CMD ["npm", "start"]
