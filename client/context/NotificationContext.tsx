"use client";
import {
  AlertCircle,
  AlertTriangle,
  CheckCircle2,
  Info,
  X,
} from "lucide-react";
import {
  createContext,
  ReactNode,
  useEffect,
  useState,
  useRef,
  useContext,
} from "react";

const NotificationContext = createContext<{
  addNotification: (args: {
    title: string;
    message: string;
    type: NotificationType;
  }) => void;
}>({ addNotification: () => {} });

export function NotificationProvider({ children }: { children: ReactNode }) {
  const [notifications, setNotifications] = useState<Notification[]>([]);

  const timerRef = useRef<NodeJS.Timeout | null>(null);

  const resumeTimer = () => {
    timerRef.current = setInterval(() => {
      setNotifications((pre) => {
        if (pre.length === 0 && timerRef.current !== null) {
          clearInterval(timerRef.current);
          return [];
        }
        const updatedArray = [...pre];
        updatedArray.shift();
        return updatedArray;
      });
    }, 5000);
  };

  const pauseTimer = () => {
    if (timerRef.current === null) {
      return;
    }
    clearInterval(timerRef.current);
  };

  const removeNotification = (id: number) => {
    setNotifications((pre) => {
      return pre.filter((noti) => noti.id !== id);
    });
  };

  const addNotification = ({
    title,
    message,
    type,
  }: {
    title: string;
    message: string;
    type: NotificationType;
  }) => {
    setNotifications((pre) => {
      if (pre.length == 5) {
        pre.shift();
      }
      return [...pre, { id: Date.now() + Math.random(), title, message, type }];
    });
    if (timerRef.current === null) {
      resumeTimer();
    }
  };

  return (
    <NotificationContext value={{ addNotification }}>
      {children}

      {/* Notification Container */}
      <div className="fixed bottom-4 right-4 z-50 flex flex-col-reverse gap-3 w-80 max-w-[calc(100vw-2rem)]">
        {notifications.map((notification, index) => {
          const styles = getNotificationStyles(notification.type);
          return (
            <div
              key={index}
              onMouseEnter={() => pauseTimer()}
              onMouseLeave={() => resumeTimer()}
              className={`${styles.bg} ${styles.border} border rounded-lg shadow-lg p-4 transition-all duration-300 animate-in slide-in-from-right cursor-default`}
            >
              <div className="flex gap-3">
                <div className="shrink-0 mt-0.5">{styles.icon}</div>
                <div className="flex-1 min-w-0">
                  <p className={`text-sm font-semibold ${styles.titleColor}`}>
                    {notification.title}
                  </p>
                  <p className={`text-sm mt-1 ${styles.messageColor}`}>
                    {notification.message}
                  </p>
                </div>
                <button
                  onClick={() => removeNotification(notification.id)}
                  className="shrink-0 text-muted-foreground hover:text-foreground transition-colors"
                >
                  <X className="h-4 w-4" />
                </button>
              </div>
            </div>
          );
        })}
      </div>
    </NotificationContext>
  );
}

export default function useNotificationContext() {
  return useContext(NotificationContext);
}

const getNotificationStyles = (type: NotificationType) => {
  switch (type) {
    case "success":
      return {
        bg: "bg-green-50 dark:bg-green-950/50",
        border: "border-green-200 dark:border-green-800",
        icon: (
          <CheckCircle2 className="h-5 w-5 text-green-600 dark:text-green-400" />
        ),
        titleColor: "text-green-900 dark:text-green-100",
        messageColor: "text-green-700 dark:text-green-300",
      };
    case "error":
      return {
        bg: "bg-red-50 dark:bg-red-950/50",
        border: "border-red-200 dark:border-red-800",
        icon: (
          <AlertCircle className="h-5 w-5 text-red-600 dark:text-red-400" />
        ),
        titleColor: "text-red-900 dark:text-red-100",
        messageColor: "text-red-700 dark:text-red-300",
      };
    case "warning":
      return {
        bg: "bg-yellow-50 dark:bg-yellow-950/50",
        border: "border-yellow-200 dark:border-yellow-800",
        icon: (
          <AlertTriangle className="h-5 w-5 text-yellow-600 dark:text-yellow-400" />
        ),
        titleColor: "text-yellow-900 dark:text-yellow-100",
        messageColor: "text-yellow-700 dark:text-yellow-300",
      };
    case "info":
    default:
      return {
        bg: "bg-blue-50 dark:bg-blue-950/50",
        border: "border-blue-200 dark:border-blue-800",
        icon: <Info className="h-5 w-5 text-blue-600 dark:text-blue-400" />,
        titleColor: "text-blue-900 dark:text-blue-100",
        messageColor: "text-blue-700 dark:text-blue-300",
      };
  }
};
export type NotificationType = "success" | "error" | "info" | "warning";
export type Notification = {
  id: number;
  title: string;
  message: string;
  type: NotificationType;
};

/*
const timersRef = React.useRef({});

  const addNotification = (type, title, message) => {
    const id = Date.now() + Math.random();
    const newNotification = {
      id,
      type,
      title,
      message,
    };

    setNotifications((prev) => {
      const updated = [newNotification, ...prev].slice(0, 5);
      return updated;
    });

    // Set auto-dismiss timer
    timersRef.current[id] = setTimeout(() => {
      removeNotification(id);
    }, 5000);
  };

  const removeNotification = (id) => {
    if (timersRef.current[id]) {
      clearTimeout(timersRef.current[id]);
      delete timersRef.current[id];
    }
    setNotifications((prev) => prev.filter((notif) => notif.id !== id));
  };

  const pauseTimer = (id) => {
    if (timersRef.current[id]) {
      clearTimeout(timersRef.current[id]);
      delete timersRef.current[id];
    }
  };

  const resumeTimer = (id) => {
    if (!timersRef.current[id]) {
      timersRef.current[id] = setTimeout(() => {
        removeNotification(id);
      }, 5000);
    }
*/
