import { useEffect, useState } from 'react';

type ProcessedMessage = {
  id: string;
  message: string;
  createdAt: string;
  uppercaseMessage: string;
  processedAt: string;
};

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

export default function App() {
  const [messages, setMessages] = useState<ProcessedMessage[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    let active = true;
    const loadMessages = async () => {
      try {
        const response = await fetch(`${API_URL}/api/messages`);
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        const data: ProcessedMessage[] = await response.json();
        if (active) {
          setMessages(data);
          setError('');
        }
      } catch {
        if (active) setError('Не удаётся подключиться к сервису сообщений. Повторяем запрос…');
      }
    };
    void loadMessages();
    const timer = window.setInterval(() => void loadMessages(), 2000);
    return () => {
      active = false;
      window.clearInterval(timer);
    };
  }, []);

  return (
    <main className="container">
      <header>
        <p className="eyebrow">SPRING BOOT · KAFKA STREAMS</p>
        <h1>Монитор сообщений</h1>
        <p>Сервис обновляет список каждые две секунды.</p>
        <a className="button" href="http://localhost:8080" target="_blank" rel="noreferrer">
          Отправить сообщение через Thymeleaf
        </a>
      </header>
      {error && <p className="error" role="status">{error}</p>}
      {!error && messages.length === 0 && <p className="empty">Пока сообщений нет. Отправьте первое через форму.</p>}
      <section className="messages" aria-live="polite">
        {messages.map((item) => (
          <article className="card" key={item.id}>
            <p className="message">{item.message}</p>
            <p><span>После обработки:</span> <strong>{item.uppercaseMessage}</strong></p>
            <small>Принято: {new Date(item.createdAt).toLocaleString('ru-RU')}</small>
          </article>
        ))}
      </section>
    </main>
  );
}
