import {signal} from "@vaadin/hilla-react-signals";
import {State} from "@vaadin/hilla-frontend";
import connectClient from "Frontend/generated/connect-client.default";

export const connectionActive = signal(connectClient.fluxConnection.state === State.ACTIVE);

connectClient.fluxConnection.addEventListener('state-changed', (event: CustomEvent<{ active: boolean }>) => {
  connectionActive.value = event.detail.active;
});